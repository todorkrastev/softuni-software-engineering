package com.example.football.service.impl;

import com.example.football.constant.GlobalConstant;
import com.example.football.models.dto.TownSeedDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(GlobalConstant.TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        TownSeedDto[] townSeedDtos = this.gson.fromJson(readTownsFileContent(), TownSeedDto[].class);

        Arrays
                .stream(townSeedDtos)
                .filter(townSeedDto -> {
                    boolean isValid = this.validationUtil.isValid(townSeedDto) &&
                            !doesEntityExist(townSeedDto.getName());


                    stringBuilder
                            .append(isValid ?
                                    String.format("Successfully imported Town %s - %d",
                                            townSeedDto.getName(), townSeedDto.getPopulation()) :
                                    "Invalid Town")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(townSeedDto -> this.modelMapper.map(townSeedDto, Town.class))
                .forEach(this.townRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public boolean doesEntityExist(String name) {
        return this.townRepository.existsByName(name);
    }

    @Override
    public Town findByName(String name) {
        return this.townRepository
                .findByName(name)
                .orElse(null);
    }
}
