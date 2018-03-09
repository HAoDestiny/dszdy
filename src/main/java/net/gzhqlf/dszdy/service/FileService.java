package net.gzhqlf.dszdy.service;

import net.gzhqlf.dszdy.entity.FileEntity;
import net.gzhqlf.dszdy.po.FilePo;
import net.gzhqlf.dszdy.repository.FileRepository;
import net.gzhqlf.dszdy.util.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by n0elle on 2017/10/24.
 */

@Service
public class FileService {

    @Value("${dszdy.project.imagesProtocol}")
    private String IMAGES_PROTOCOL;

    @Resource
    private FileRepository fileRepository;

    public FilePo getFileByFileId(int fileId) {
        FileEntity fileEntity = fileRepository.findOne(fileId);

        FilePo filePo = new FilePo();
        filePo.setFileHost(IMAGES_PROTOCOL);

        if (null == fileEntity) {
            filePo.setFileName("userface_normal");
            filePo.setFileType(".jpg");
            return filePo;
        }

        if (fileEntity.getDeleteTag() != 1) {
            filePo.setFileName(Tool.getFileName(fileEntity.getFileUri()));
            filePo.setFileType(Tool.getExtName(fileEntity.getFileUri()));
        }

        return filePo;
    }
}
