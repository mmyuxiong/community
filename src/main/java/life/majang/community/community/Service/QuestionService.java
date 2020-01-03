package life.majang.community.community.Service;

import life.majang.community.community.Dto.PaginationDTO;
import life.majang.community.community.Dto.QuestionDTO;
import life.majang.community.community.Mapper.QuestionMapper;
import life.majang.community.community.Mapper.UserMapper;
import life.majang.community.community.model.Question;
import life.majang.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;


    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer total=questionMapper.count();
        paginationDTO.setPagination(total,page,size);
        if(page<1){
            page=1;
        }
        if (page>paginationDTO.getPage()){
            page=paginationDTO.getPage();
        }
        Integer offset=size*(page-1);
        List<Question> list = questionMapper.list(offset,size);
        List<QuestionDTO>questionDTOList=new ArrayList<>();
        for (Question question : list) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer accountId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer total=questionMapper.countByUserId(accountId);
        paginationDTO.setPagination(total,page,size);
        if(page<1){
            page=1;
        }
        if (page>paginationDTO.getPage()){
            page=paginationDTO.getPage();
        }
        Integer offset=size*(page-1);
        List<Question> list = questionMapper.listByUserId(accountId,offset,size);
        List<QuestionDTO>questionDTOList=new ArrayList<>();
        for (Question question : list) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
       Question question= questionMapper.getById(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        return questionDTO;
    }
}
