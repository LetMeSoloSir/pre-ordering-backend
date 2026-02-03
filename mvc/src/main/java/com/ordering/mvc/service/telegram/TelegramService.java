package com.ordering.mvc.service.telegram;

import com.ordering.mvc.model.order.OrderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TelegramService {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.chat-id}")
    private String chatId;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendOrderPaidMessage(OrderInfo order) {

        String message = """
                ✅ ĐƠN HÀNG ĐÃ THANH TOÁN
                🧾 Mã đơn: %s
                💰 Tổng tiền: %d
                💳 Thanh toán: %s
                🕒 Thời gian: %s
                """.formatted(
                order.getOrderNumber(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getPaidAt()
        );

        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";

        Map<String, Object> body = Map.of(
                "chat_id", chatId,
                "text", message
        );

        restTemplate.postForObject(url, body, String.class);
    }
}

