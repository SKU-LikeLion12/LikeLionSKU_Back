package likelion.sku_sku.service;

import likelion.sku_sku.domain.Project;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.exception.InvalidTitleException;
import likelion.sku_sku.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static likelion.sku_sku.dto.ProjectDTO.ResponseIdProjectUpdate;
import static likelion.sku_sku.dto.ProjectDTO.ResponseProjectUpdate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public Project addProject(String classTh, String title, String subTitle, String url, MultipartFile image) throws IOException {
        if (projectRepository.findByTitle(title).isPresent()) {
            throw new InvalidTitleException();
        }
        byte[] imageBytes = image.getBytes();
        Project project = new Project(classTh, title, subTitle, url, imageBytes);
        return projectRepository.save(project);
    }

    @Transactional
    public Project updateProject(Long id, String classTh, String title, String subTitle, String url, MultipartFile image) throws IOException {
        Project project = projectRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        if (image != null && !image.isEmpty()) {
            project.setImage(image);
        }
        String newClassTh = (classTh != null && !classTh.isEmpty() ? classTh : project.getClassTh());
        String newTitle = (title != null && !title.isEmpty() ? title : project.getTitle());
        if (!newTitle.equals(project.getTitle()) && projectRepository.findByTitle(title).isPresent()) {
            throw new InvalidTitleException();
        }
        String newSubTitle = (subTitle != null && !subTitle.isEmpty() ? subTitle : project.getSubTitle());
        String newUrl = (url != null && !url.isEmpty() ? url : project.getUrl());
        project.changeProject(newClassTh, newTitle, newSubTitle, newUrl);
        return project;
    }

    public List<ResponseIdProjectUpdate> findProjectAll() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(project -> new ResponseIdProjectUpdate(
                        project.getId(),
                        project.getClassTh(),
                        project.getTitle(),
                        project.getSubTitle(),
                        project.getUrl(),
                        project.arrayToImage() // 이미지 바이트 배열을 Base64 문자열로 변환
                ))
                .collect(Collectors.toList());
    }

    public ResponseProjectUpdate findProjectById(Long id) {
        return projectRepository.findById(id)
                .map(project -> new ResponseProjectUpdate(
                        project.getClassTh(),
                        project.getTitle(),
                        project.getSubTitle(),
                        project.getUrl(),
                        project.arrayToImage()))
                .orElseThrow(InvalidIdException::new);
    }

    @Transactional
    public void deleteProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        projectRepository.delete(project);
    }
}
