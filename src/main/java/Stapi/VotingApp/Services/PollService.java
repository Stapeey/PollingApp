package Stapi.VotingApp.Services;

import Stapi.VotingApp.Dto.PollDto;
import Stapi.VotingApp.Models.Poll;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PollService {
    void create(PollDto pollDto);

    List<Poll> getPolls();

    Poll getPollById(Long pollId);

    void deletePoll(Long pollId);

    void vote(Long pollId, String username, Integer option);

    List<Float> getVotes(Long pollId);



}
