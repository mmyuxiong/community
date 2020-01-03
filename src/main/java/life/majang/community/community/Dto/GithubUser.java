package life.majang.community.community.Dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private String bio;
    private Long id;
    private String avatarUrl;


    @Override
    public String toString() {
        return "GithubUser{" +
                "name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", id=" + id +
                '}';
    }
}
