package likelion.sku_sku.service;

import likelion.sku_sku.domain.Project;
import likelion.sku_sku.exception.IllegalProjectException;
import likelion.sku_sku.exception.IllegalTitleException;
import likelion.sku_sku.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static likelion.sku_sku.dto.ProjectDTO.ResponseProjectUpdate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public Project addProject(String classTh, String title, String subTitle, MultipartFile image) throws IOException {
        if (projectRepository.findByTitle(title).isPresent()) {
            throw new IllegalTitleException("그 title 이미 있");
        }
        byte[] imageBytes = image.getBytes();
        Project project = new Project(classTh, title, subTitle, imageBytes);
        return projectRepository.save(project);
    }

    @Transactional
    public Project updateProject(Long id, String classTh, String title, String subTitle, MultipartFile image) throws IOException {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalProjectException("그런 id 가진 project 없"));
        if (projectRepository.findByTitle(title).isPresent()) {
            throw new IllegalTitleException("그 title 이미 있");
        }
        if (image != null && !image.isEmpty()) {
            project.setImage(image);
        }

        String newClassTh = (classTh != null && !classTh.isEmpty() ? classTh : project.getClassTh());
        String newTitle = (title != null && !title.isEmpty() ? title : project.getTitle());
        String newSubTitle = (subTitle != null && !subTitle.isEmpty() ? subTitle : project.getSubTitle());
        project.changeProject(newClassTh, newTitle, newSubTitle);
        return project;
    }

    public List<Project> findProjectAll() {
        return projectRepository.findAll();
    }

    public ResponseProjectUpdate findProjectById(Long id) {
        return projectRepository.findById(id)
                .map(project -> new ResponseProjectUpdate(
                        project.getClassTh(),
                        project.getTitle(),
                        project.getSubTitle(),
                        project.arrayToImage()))
                .orElse(null);
    }

    @Transactional
    public void deleteProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalProjectException("그런 id 가진 Project 없"));
        projectRepository.delete(project);
    }
}
