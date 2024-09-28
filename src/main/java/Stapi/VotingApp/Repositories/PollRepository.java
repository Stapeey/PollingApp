package Stapi.VotingApp.Repositories;

import Stapi.VotingApp.Models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

    Poll save(Poll poll);
}
