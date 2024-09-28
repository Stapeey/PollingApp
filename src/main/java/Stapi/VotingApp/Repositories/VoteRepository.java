package Stapi.VotingApp.Repositories;

import Stapi.VotingApp.Models.Poll;
import Stapi.VotingApp.Models.UserEntity;
import Stapi.VotingApp.Models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUserAndPoll(UserEntity user, Poll poll);

    Float countByPollAndOptionId(Poll poll, Integer optionId);

    Integer countByPoll(Poll poll);
}
