package smallmart.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobAsyncClient;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import smallmart.model.Product;
import smallmart.repository.ItemRepo;
import smallmart.repository.ProductRepo;
import smallmart.service.ProductService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//--------------------------------for employee, manager--------------------------------
@Controller
public class ProductController {
    @Autowired
//    private ProductRepo productRepo;
    private ProductService productService;



    //------------------------------ EDIT -----------------------------------------------------
    @RequestMapping(value = "/editProduct/{editId}", method = RequestMethod.GET)
    public String index(Model model, @PathVariable String editId){  //
        Optional<Product> product = productService.findById(Long.valueOf(editId));
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "editProduct";
        } else {
            model.addAttribute("error", "Извините, информация по товару отсутствует!");
            return "errorPage";
        }
    }

    @PostMapping("/editProduct")
    public String edit(Model model, @ModelAttribute Product product, @RequestParam("file") MultipartFile file)
            throws IOException{ //
        if(product == null) {
            model.addAttribute("error", "Извините, информация по товару отсутствует!");
            return "errorPage";
        }
        Optional<Product> prodOption = productService.findById(product.getId());
        if(prodOption.isPresent()){
            productService.productUpdate(product, file, prodOption);
        }
        List<Product> products =  productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("size", products.size());
        return "main";
    }

    //---------------------------- SAVE --------------------------------------------------------------
    @PostMapping("/save")
    public String save(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        if(product == null)
            System.out.println("Product isNull!");
        else
            System.out.println("product.title: " + product.getTitle());
        productService.uploadOnAzure(product, file);
        Product savedProduct = productService.save(product);
        if (savedProduct != null){
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
            model.addAttribute("size", products.size());
            return "main";
        } else {
            model.addAttribute("error", "Не удалось сохранить товар!");
            return "error_page";
        }
    }



    //------------------------------ DELETE -------------------------------------------------------
    @GetMapping("/delete/{delId}")
    @Transactional
    public String delete(Map<String, Object> model, @PathVariable String delId){
        Optional<Product> product = productService.findById(Long.valueOf(delId));
        if(product.isPresent())
            productService.deleteById(Long.valueOf(delId));

        List<Product> products = productService.findAll();
        model.put("size", products.size());
        model.put("products", products);
        return "main";
    }
}
