package ru.itis.Tyshenko.services;

import ru.itis.Tyshenko.dto.AdDTO;
import ru.itis.Tyshenko.entity.Ad;
import ru.itis.Tyshenko.repositories.posts.AdRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AdServiceImpl implements AdService {

    private final AdRepository repository;


    public AdServiceImpl(AdRepository repository) {
        this.repository = repository;
    }


    @Override
    public void add(AdDTO adDTO, Long userId) {
        Ad ad = Ad.builder().id(null).header(adDTO.getHeader())
                .description(adDTO.getDescription()).contact(adDTO.getContact())
                .price(Long.parseLong(adDTO.getPrice()))
                .user_id(userId).resume_id(0L).build();
        repository.save(ad);
        adDTO.setId(ad.getId());
    }

    @Override
    public List<AdDTO> getAll() {
        List<Ad> ads =  repository.findAll();
        return convertAds(ads);
    }

    @Override
    public Optional<AdDTO> getById(Long id) {
        Optional<Ad> optionalAd = repository.getById(id);
        if (optionalAd.isPresent()) {
            Ad ad = optionalAd.get();
            return Optional.of(AdDTO.builder().id(ad.getId()).description(ad.getDescription()).
                    contact(ad.getContact()).header(ad.getHeader()).
                    price(ad.getPrice().toString()).user_id(ad.getUser_id()).build());
        }
        return Optional.empty();
    }

    @Override
    public List<AdDTO> getAllByUserID(Long user_id) {
        List<Ad> ads = repository.getAllByUserID(user_id);
        return convertAds(ads);
    }

    @Override
    public Optional<List<AdDTO>> getAllByResumeId(Long resume_id) {
        List<AdDTO> adDTOS = null;
        Optional<List<Ad>> ads = repository.getByResumeID(resume_id);
        if (ads.isPresent()) {
            adDTOS =  convertAds(ads.get());
        }
        return Optional.ofNullable(adDTOS);
    }

    private List<AdDTO> convertAds(List<Ad> ads) {
        List<AdDTO> adDTOS = new LinkedList<>();
        for (Ad ad: ads) {
            AdDTO adDTO = AdDTO.builder().id(ad.getId()).description(ad.getDescription()).
                    contact(ad.getContact()).header(ad.getHeader()).
                    price(ad.getPrice().toString()).user_id(ad.getUser_id()).build();
            adDTOS.add(adDTO);
        }
        return adDTOS;
    }
}
