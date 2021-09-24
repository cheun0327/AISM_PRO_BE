//package com.upvote.aismpro.settings;
//
//import com.upvote.aismpro.entity.User;
//import com.upvote.aismpro.repository.EmailRepository;
//import com.upvote.aismpro.service.EmailService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//
//import java.util.Optional;
//
//@Configuration
//public class EmailConfig {
//
//    @Bean
//    public EmailRepository emailRepository() {
//        return new EmailRepository() {
//            @Override
//            public <S extends User> S save(S entity) {
//                return null;
//            }
//
//            @Override
//            public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
//                return null;
//            }
//
//            @Override
//            public Optional<User> findById(Long aLong) {
//                return Optional.empty();
//            }
//
//            @Override
//            public boolean existsById(Long aLong) {
//                return false;
//            }
//
//            @Override
//            public Iterable<User> findAll() {
//                return null;
//            }
//
//            @Override
//            public Iterable<User> findAllById(Iterable<Long> longs) {
//                return null;
//            }
//
//            @Override
//            public long count() {
//                return 0;
//            }
//
//            @Override
//            public void deleteById(Long aLong) {
//
//            }
//
//            @Override
//            public void delete(User entity) {
//
//            }
//
//            @Override
//            public void deleteAllById(Iterable<? extends Long> longs) {
//
//            }
//
//            @Override
//            public void deleteAll(Iterable<? extends User> entities) {
//
//            }
//
//            @Override
//            public void deleteAll() {
//
//            }
//        };
//    }
//
//    @Bean
//    public EmailService emailService() {
//        return new EmailService((JavaMailSender) emailRepository());
//    }
//}
