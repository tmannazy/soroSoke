package africa.semicolon.soroSoke.services;

import africa.semicolon.soroSoke.data.models.Atiku;
import africa.semicolon.soroSoke.data.repositories.AtikuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AtikuServiceImpl implements AtikuService{

    @Autowired
    private AtikuRepository atikuRepository;

    @Override
    public Collection<Atiku> allAtikus() {
        return atikuRepository.findAll();
    }
//    @Autowired
//    private AtikuRepository atikuRepository;
}
