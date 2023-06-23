package com.cyberpoint.service.impl;

import com.cyberpoint.domain.CallBack;
import com.cyberpoint.repository.CallBackRepository;
import com.cyberpoint.service.CallBackService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CallBack}.
 */
@Service
@Transactional
public class CallBackServiceImpl implements CallBackService {

    private final Logger log = LoggerFactory.getLogger(CallBackServiceImpl.class);

    private final CallBackRepository callBackRepository;

    public CallBackServiceImpl(CallBackRepository callBackRepository) {
        this.callBackRepository = callBackRepository;
    }

    @Override
    public CallBack save(CallBack callBack) {
        log.debug("Request to save CallBack : {}", callBack);
        return callBackRepository.save(callBack);
    }

    @Override
    public CallBack update(CallBack callBack) {
        log.debug("Request to update CallBack : {}", callBack);
        return callBackRepository.save(callBack);
    }

    @Override
    public Optional<CallBack> partialUpdate(CallBack callBack) {
        log.debug("Request to partially update CallBack : {}", callBack);

        return callBackRepository
            .findById(callBack.getId())
            .map(existingCallBack -> {
                if (callBack.getIpAddress() != null) {
                    existingCallBack.setIpAddress(callBack.getIpAddress());
                }
                if (callBack.getUrl() != null) {
                    existingCallBack.setUrl(callBack.getUrl());
                }
                if (callBack.getRemotePort() != null) {
                    existingCallBack.setRemotePort(callBack.getRemotePort());
                }
                if (callBack.getTimestamp() != null) {
                    existingCallBack.setTimestamp(callBack.getTimestamp());
                }
                if (callBack.getBuffer() != null) {
                    existingCallBack.setBuffer(callBack.getBuffer());
                }
                if (callBack.getRawcontents() != null) {
                    existingCallBack.setRawcontents(callBack.getRawcontents());
                }
                if (callBack.getRawcontentsContentType() != null) {
                    existingCallBack.setRawcontentsContentType(callBack.getRawcontentsContentType());
                }

                return existingCallBack;
            })
            .map(callBackRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CallBack> findAll(Pageable pageable) {
        log.debug("Request to get all CallBacks");
        return callBackRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CallBack> findOne(Long id) {
        log.debug("Request to get CallBack : {}", id);
        return callBackRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CallBack : {}", id);
        callBackRepository.deleteById(id);
    }
}
