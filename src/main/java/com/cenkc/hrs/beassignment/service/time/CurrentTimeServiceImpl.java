package com.cenkc.hrs.beassignment.service.time;

import com.cenkc.hrs.beassignment.model.CurrentTimeServiceResponseBean;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * created by cenkc on 3/9/2020
 */
@Service
public class CurrentTimeServiceImpl implements CurrentTimeService {

    @Override
    public CurrentTimeServiceResponseBean getCurrentTime() {
        return new CurrentTimeServiceResponseBean(new Date(System.currentTimeMillis()));
    }

}
