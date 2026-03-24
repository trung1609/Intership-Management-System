package com.trung.service.impl;

import com.trung.repository.IMentorRepository;
import com.trung.service.IMentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements IMentorService {
    private final IMentorRepository mentorRepository;
}
