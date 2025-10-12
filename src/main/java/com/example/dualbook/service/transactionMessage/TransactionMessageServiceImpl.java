package com.example.dualbook.service.transactionMessage;

import com.example.dualbook.entity.Transaction;
import com.example.dualbook.entity.TransactionMessage;
import com.example.dualbook.entity.User;
import com.example.dualbook.repository.TransactionMessageRepository;
import com.example.dualbook.repository.TransactionRepository;
import com.example.dualbook.service.transaction.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionMessageServiceImpl implements com.example.dualbook.service.message.TransactionMessageService {

    private final TransactionMessageRepository messageRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public TransactionMessageServiceImpl(TransactionMessageRepository messageRepository,
                                         TransactionRepository transactionRepository,
                                         TransactionService transactionService) {
        this.messageRepository = messageRepository;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    @Override
    public TransactionMessage sendMessage(User sender, Long transactionId, String message) {
        Transaction transaction = transactionService.findById(transactionId);

        // بررسی اینکه کاربر مجاز به ارسال پیام برای این تراکنش است
        if (!transaction.getSender().equals(sender) && !transaction.getReceiver().equals(sender)) {
            throw new RuntimeException("Not authorized to send message for this transaction");
        }

        TransactionMessage transactionMessage = new TransactionMessage();
        transactionMessage.setTransaction(transaction);
        transactionMessage.setSender(sender);
        transactionMessage.setMessage(message);

        return messageRepository.save(transactionMessage);
    }

    @Override
    public List<TransactionMessage> getTransactionMessages(Long transactionId) {
        Transaction transaction = transactionService.findById(transactionId);
        return messageRepository.findByTransactionIdAndActive(transactionId);
    }

    @Override
    public TransactionMessage getMessageById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }

    @Override
    public TransactionMessage updateMessage(Long messageId, User user, String newMessage) {
        TransactionMessage message = getMessageById(messageId);

        // فقط فرستنده پیام می‌تواند آن را به‌روزرسانی کند
        if (!message.getSender().equals(user)) {
            throw new RuntimeException("Only message sender can update the message");
        }

        // فقط پیام‌های جدید (کمتر از 5 دقیقه) قابل ویرایش هستند
        if (message.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
            throw new RuntimeException("Cannot edit messages older than 5 minutes");
        }

        message.setMessage(newMessage);
        return messageRepository.save(message);
    }

    @Override
    public void deleteMessage(Long messageId, User user) {
        TransactionMessage message = getMessageById(messageId);

        // فقط فرستنده پیام می‌تواند آن را حذف کند
        if (!message.getSender().equals(user)) {
            throw new RuntimeException("Only message sender can delete the message");
        }

        message.setDisableDate(LocalDateTime.now());
        messageRepository.save(message);
    }
}