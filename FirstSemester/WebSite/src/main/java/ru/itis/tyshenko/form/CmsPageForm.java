package ru.itis.tyshenko.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CmsPageForm {
    @NotBlank
    private String name;
    @NotBlank
    private String content;
}
