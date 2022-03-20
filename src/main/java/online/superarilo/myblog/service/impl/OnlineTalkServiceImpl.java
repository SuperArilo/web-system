package online.superarilo.myblog.service.impl;

import online.superarilo.myblog.mapper.OnlineTalkMapper;
import online.superarilo.myblog.service.OnlineTalkService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OnlineTalkServiceImpl implements OnlineTalkService {

    final OnlineTalkMapper onlineTalkMapper;

    public OnlineTalkServiceImpl(OnlineTalkMapper onlineTalkMapper) {
        this.onlineTalkMapper = onlineTalkMapper;
    }

    @Override
    public Map<String,Object> getUserHeadAndName(String mcJavaId, String uuid) {
        return onlineTalkMapper.getUserHeadAndName(mcJavaId, uuid);
    }
}
