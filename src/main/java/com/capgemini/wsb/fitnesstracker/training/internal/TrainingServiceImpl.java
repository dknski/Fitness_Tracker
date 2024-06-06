package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingProvider {
    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository, UserRepository userRepository) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    public TrainingDto createTraining(TrainingDto trainingDto) {
        User user = userRepository.findById(trainingDto.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Training training = toEntity(trainingDto, user);
        return toDto(trainingRepository.save(training));
    }

    @Override
    public List<TrainingDto> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(TrainingDto::new)
                .collect(Collectors.toList());
    }

    public List<TrainingDto> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId).stream()
                .map(TrainingDto::new)
                .collect(Collectors.toList());
    }

    public List<TrainingDto> getFinishedTrainingsAfter(String afterTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(afterTime);
        List<Training> trainings = trainingRepository.findByDateAfter(date);
        return trainings.stream().map(this::toDto).collect(Collectors.toList());
    }


    public List<TrainingDto> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType).stream()
                .map(TrainingDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TrainingDto updateTraining(Long trainingId, TrainingDto trainingDto) {
        Training existingTraining = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new RuntimeException(String.valueOf(trainingId)));

        if (trainingDto.getActivityType() != null) {
            existingTraining.setActivityType(trainingDto.getActivityType());
        }
        if (trainingDto.getDistance() != null) {
            existingTraining.setDistance(trainingDto.getDistance());
        }
        if (trainingDto.getStartTime() != null) {
            existingTraining.setStartTime(trainingDto.getStartTime());
        }
        if (trainingDto.getEndTime() != null) {
            existingTraining.setEndTime(trainingDto.getEndTime());
        }
        if (trainingDto.getAverageSpeed() != null) {
            existingTraining.setAverageSpeed(trainingDto.getAverageSpeed());
        }

        trainingRepository.save(existingTraining);
        return toDto(existingTraining);
    }

    private Training toEntity(TrainingDto dto, User user) {
        return new Training(
                user,
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getActivityType(),
                dto.getDistance(),
                dto.getAverageSpeed()
        );
    }

    private TrainingDto toDto(Training training) {
        return new TrainingDto(training);
    }
}
