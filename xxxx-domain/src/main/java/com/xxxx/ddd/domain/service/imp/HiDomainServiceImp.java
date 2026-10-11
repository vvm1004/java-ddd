package com.xxxx.ddd.domain.service.imp;

import com.xxxx.ddd.domain.respository.HiDomainRepository;
import com.xxxx.ddd.domain.service.HiDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiDomainServiceImp implements HiDomainService {
    @Autowired
    private HiDomainRepository hiDomainRepository;

    @Override
    public String sayHi(String who) {
        return hiDomainRepository.sayHi(who);
    }
}
