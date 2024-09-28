package Stapi.VotingApp.Controllers;

import Stapi.VotingApp.Dto.PollDto;
import Stapi.VotingApp.Models.Poll;
import Stapi.VotingApp.Services.Impl.PollServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class PollController {
    private PollServiceImpl pollService;

    @Autowired
    public PollController(PollServiceImpl pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model){
        PollDto pollDto = new PollDto();
        model.addAttribute("data", pollDto);
        return "create-polls";
    }

    @PostMapping("/create")
    public String createPoll(@ModelAttribute PollDto pollDto){
        pollService.create(pollDto);
        return "redirect:/polls";
    }

    @GetMapping("/polls")
    public String polls(Model model){
        List<Poll> polls = pollService.getPolls();
        model.addAttribute("polls", polls);
        return "list-polls";
    }

    @PostMapping("/delete/{pollId}")
    public String delete(@PathVariable Long pollId){
        pollService.deletePoll(pollId);
        return "redirect:/polls";
    }

    @GetMapping("/polls/{pollId}")
    public String view(@PathVariable Long pollId, Model model){
        Poll poll =  pollService.getPollById(pollId);
        List<Float> votes = pollService.getVotes(pollId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        model.addAttribute("poll", poll);
        model.addAttribute("pollId", pollId);
        model.addAttribute("votes", votes);
        model.addAttribute("user", user);
        return "view-poll";
    }

    @PostMapping("/polls/{pollId}/{option}")
    public String vote(@PathVariable("pollId")Long pollId, @PathVariable("option") Integer option){
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        pollService.vote(pollId,currentUser.getName(),option);
        return "redirect:/polls/" + pollId;
    }

}
