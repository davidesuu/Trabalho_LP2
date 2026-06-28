package com.exemplo.ufmaextensao.service;

import com.exemplo.ufmaextensao.entity.Log;
import com.exemplo.ufmaextensao.repository.LogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Autowired
    private LogRepo logRepo;

    /**
     * Essa função recebe um log e salva no repositorio de log
     * @param log
     */
    public Log salvarLog(Log log) {
        return logRepo.save(log);
    }


}
