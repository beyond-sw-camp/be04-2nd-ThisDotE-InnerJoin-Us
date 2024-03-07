package org.thisdote.innerjoinus.articlereply.article.command.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thisdote.innerjoinus.articlereply.article.command.aggregate.ArticleEntity;
import org.thisdote.innerjoinus.articlereply.article.command.repository.CommandArticleRepository;
import org.thisdote.innerjoinus.articlereply.article.dto.ArticleDTO;

@Service
public class CommandArticleServiceImpl implements CommandArticleService {
    private final ModelMapper mapper;
    private final CommandArticleRepository commandArticleRepository;

    @Autowired
    public CommandArticleServiceImpl(ModelMapper mapper, CommandArticleRepository commandArticleRepository) {
        this.mapper = mapper;
        this.commandArticleRepository = commandArticleRepository;
    }

    @Transactional
    public void registArticle(ArticleDTO newArticle){
        commandArticleRepository.save(mapper.map(newArticle, ArticleEntity.class));
    }

    @Transactional
    @Override
    public String deleteArticle(ArticleDTO articleDTO) {
        ArticleEntity article = commandArticleRepository.findById(articleDTO.getArticleId()).get();
        if(article.getArticleDeleteStatus() == 0){
            return "이미 삭제된 게시글입니다.";
        } else{
            article.setArticleDeleteStatus(articleDTO.getArticleDeleteStatus());
            return "게시글이 삭제 되었습니다.";
        }
    }

    @Transactional
    public void modifyArticle(ArticleDTO articleDTO) {
        commandArticleRepository.save(mapper.map(articleDTO, ArticleEntity.class));
    }
}
