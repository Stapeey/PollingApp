package Stapi.VotingApp.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollDto {
    private String title;
    private String photoUrl;
    private String description;
    private List<String> options;
    private List<String> photoUrls;
}
