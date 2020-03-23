package smallmart.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import smallmart.model.Product;
import smallmart.repository.ProductRepo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

    public void productUpdate(Product product, MultipartFile file, Optional<Product> prodOption) {
        Product productDB = prodOption.get();
        if(file != null) {
            try {
                uploadOnAzure(productDB, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productDB.setPrice(product.getPrice());
        productDB.setTitle(product.getTitle());
        productRepo.save(productDB);
    }
    public void uploadOnAzure(@ModelAttribute Product product, @RequestParam("file") MultipartFile file) throws IOException {
        if(file != null && !file.getOriginalFilename().isEmpty()){
            String connectStr = System.getenv("AZURE_STORAGE_CONN_SPRING"); //из переменной среды
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();
            String containerName = "img";
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            File path = uploadOnHDD(product, file);   //+будет записан на /D:/upload
            BlockBlobClient blobBlockClient = containerClient.getBlobClient(path.getName()).getBlockBlobClient();
            try(ByteArrayInputStream dataStream = new ByteArrayInputStream(Files.readAllBytes(path.toPath()))){
                blobBlockClient.upload(dataStream, path.length());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private File uploadOnHDD(@ModelAttribute Product product, @RequestParam("file") MultipartFile file) throws IOException {
        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists())
                uploadDir.mkdir();
        }
        //String uuidStr = UUID.randomUUID().toString();String resName = uuidStr + "." +
        String resName0 = file.getName();
        String resName = file.getOriginalFilename();
        File copy = new File(uploadPath + "/" + resName);
        file.transferTo(copy);
        product.setFilename(resName);
        return copy;
    }

    public List<Product> findAll() {
        return (List<Product>) productRepo.findAll();
    }

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }
}
