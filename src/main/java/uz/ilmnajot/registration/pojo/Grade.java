package uz.ilmnajot.registration.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Grade {
    private String name;
    private String subject;
    private String score;
}
