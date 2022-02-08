package com.upvote.aismpro.service;

import com.upvote.aismpro.entity.Credit;
import com.upvote.aismpro.repository.CreditRepository;
import com.upvote.aismpro.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditService {

    @Autowired
    private CreditRepository creditRepository;


    public void subtractCredit(Long amount) throws IllegalArgumentException{

        Long userId = SecurityUtil.getCurrentUserId();
        Credit credit = creditRepository.findCreditByUser_UserId(userId);

        if (credit.getCredit() < amount) throw new IllegalArgumentException("credit 부족");

        credit.updateCredit(credit.getCredit() - amount);

        creditRepository.save(credit);
    }
}
