package Stapi.VotingApp.Services.Impl;

import Stapi.VotingApp.Dto.PollDto;
import Stapi.VotingApp.Models.Poll;
import Stapi.VotingApp.Models.UserEntity;
import Stapi.VotingApp.Models.Vote;
import Stapi.VotingApp.Repositories.PollRepository;
import Stapi.VotingApp.Repositories.UserRepository;
import Stapi.VotingApp.Repositories.VoteRepository;
import Stapi.VotingApp.Services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollServiceImpl implements PollService {
    private PollRepository pollRepository;
    private VoteRepository voteRepository;
    private UserRepository userRepository;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository, VoteRepository voteRepository, UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(PollDto pollDto) {
        Poll poll = mapToPoll(pollDto);
        UserEntity user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        poll.setCreatedBy(user);
        pollRepository.save(poll);
    }

    @Override
    public List<Poll> getPolls() {
        List<Poll> polls = pollRepository.findAll();
        return polls;
    }

    @Override
    public Poll getPollById(Long pollId) {
        Poll poll = pollRepository.findById(pollId).get();
        return poll;
    }

    @Override
    public void deletePoll(Long pollId) {
        pollRepository.deleteById(pollId);
    }

    @Override
    public void vote(Long pollId, String username, Integer option) {
        Poll poll = pollRepository.findById(pollId).get();
        UserEntity user = userRepository.findByUsername(username);
        Optional<Vote> existingVote = voteRepository.findByUserAndPoll(user, poll);
        if (existingVote.isPresent()) {
            Vote vote = existingVote.get();
            vote.setOptionId(option);
            voteRepository.save(vote);
        } else {
            Vote newVote = Vote.builder()
                    .user(user)
                    .poll(poll)
                    .optionId(option)
                    .build();
            voteRepository.save(newVote);
        }
    }

    @Override
    public List<Float> getVotes(Long pollId) {
        Poll poll = pollRepository.findById(pollId).get();
        Integer OptionNumber = poll.getOptions().size();
        List<Float> votes = new ArrayList<>();
        for (int i = 0; i<OptionNumber; i++){
            votes.add(voteRepository.countByPollAndOptionId(poll, i));
        }
        Integer voteSum = voteRepository.countByPoll(poll);
        if (voteSum == 0) {
            return votes;
        }
        List<Float> votePercentages  = votes.stream().map(num -> num/voteSum * 100).collect(Collectors.toList());
        return votePercentages;
    }


    public static Poll mapToPoll(PollDto pollDto){
        Poll poll = Poll.builder()
                .title(pollDto.getTitle())
                .description(pollDto.getDescription())
                .photoUrl(pollDto.getPhotoUrl())
                .options(pollDto.getOptions())
                .photoUrls(pollDto.getPhotoUrls())
                .build();
        return poll;
    }

    public static PollDto mapToPollDto(Poll poll){
        PollDto pollDto = PollDto.builder()
                .title(poll.getTitle())
                .description(poll.getDescription())
                .photoUrl(poll.getPhotoUrl())
                .options(poll.getOptions())
                .photoUrls(poll.getPhotoUrls())
                .build();
        return pollDto;
    }
}
