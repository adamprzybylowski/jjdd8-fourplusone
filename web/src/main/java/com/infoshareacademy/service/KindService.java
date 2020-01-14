package com.infoshareacademy.service;


import com.infoshareacademy.dao.KindDao;
import com.infoshareacademy.domain.entity.LiteratureKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class KindService {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());


    @EJB
    private KindDao kindDao;

    public void addKind(String kindName){
        LiteratureKind k = new LiteratureKind();
        k.setName(kindName);
        kindDao.addKind(k);

    }

}
