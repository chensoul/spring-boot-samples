package com.chensoul.bookstore.notification.application;

import com.chensoul.bookstore.order.application.OrderEventMessage;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Value(("${app.supportEmail}"))
    private String supportEmail;

    private final JavaMailSender emailSender;

    public void sendOrderCreatedNotification(OrderEventMessage orderEventMessage) {
        String message =
                """
                        ===================================================
                        Order Created Notification
                        ----------------------------------------------------
                        Dear %s,
                        Your order with orderNumber: %s has been created successfully.
                        
                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                        .formatted(orderEventMessage.getCustomer().name(), orderEventMessage.getOrderNumber());
        log.info("\n{}", message);
        sendEmail(orderEventMessage.getCustomer().email(), "Order Created Notification", message);
    }

    public void sendOrderDeliveredNotification(OrderEventMessage orderEventMessage) {
        String message =
                """
                        ===================================================
                        Order Delivered Notification
                        ----------------------------------------------------
                        Dear %s,
                        Your order with orderNumber: %s has been delivered successfully.
                        
                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                        .formatted(orderEventMessage.getCustomer().name(), orderEventMessage.getOrderNumber());
        log.info("\n{}", message);
        sendEmail(orderEventMessage.getCustomer().email(), "Order Delivered Notification", message);
    }

    public void sendOrderCancelledNotification(OrderEventMessage orderEventMessage) {
        String message =
                """
                        ===================================================
                        Order Cancelled Notification
                        ----------------------------------------------------
                        Dear %s,
                        Your order with orderNumber: %s has been cancelled.
                        Reason: %s
                        
                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                        .formatted(orderEventMessage.getCustomer().name(), orderEventMessage.getOrderNumber(), orderEventMessage.getReason());
        log.info("\n{}", message);
        sendEmail(orderEventMessage.getCustomer().email(), "Order Cancelled Notification", message);
    }

    public void sendOrderErrorEventNotification(OrderEventMessage orderEventMessage) {
        String message =
                """
                        ===================================================
                        Order Processing Failure Notification
                        ----------------------------------------------------
                        Hi %s,
                        The order processing failed for orderNumber: %s.
                        Reason: %s
                        
                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                        .formatted(supportEmail, orderEventMessage.getOrderNumber(), orderEventMessage.getReason());
        log.info("\n{}", message);
        sendEmail(supportEmail, "Order Processing Failure Notification", message);
    }

    private void sendEmail(String recipient, String subject, String content) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(supportEmail);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content);
            emailSender.send(mimeMessage);
            log.info("Email sent to: {}", recipient);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }
}
