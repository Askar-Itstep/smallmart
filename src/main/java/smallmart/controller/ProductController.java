package smallmart.controller;

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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//--------------------------------for employee, manager--------------------------------
@Controller
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @RequestMapping(value = "/editProduct", method = RequestMethod.POST)
    public String index(@RequestParam Long editId, Model model){
        Optional<Product> product = productRepo.findById(editId);
        if (product.isPresent()) {
            model.addAttribute("product", product);
            return "productEdit";  //надо сделать!
        } else {
            model.addAttribute("error", "Извините, информация по товару отсутствует!");
            return "errorPage";
        }
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        if(product == null)
            System.out.println("Product isNull!");
        else
            System.out.println("product.title: " + product.getTitle());

        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists())
                uploadDir.mkdir();
        }
        String uuidStr = UUID.randomUUID().toString();
        String resName = uuidStr + ". " + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resName));
        product.setFilename(resName);

        Product savedProduct = productRepo.save(product);
        if (savedProduct != null){
            List<Product> products = (List<Product>) productRepo.findAll();
            model.addAttribute("products", products);
            model.addAttribute("size", products.size());
            return "main";
        } else {
            model.addAttribute("error", "Не удалось сохранить товар!");
            return "error_page";
        }
    }
    @PostMapping("/delete")
    @Transactional
    public String delete(@RequestParam(required = false) Long delId, Map<String, Object> model){
        Optional<Product> product = productRepo.findById(delId);
        if(product.isPresent())
            productRepo.deleteById(delId);

        List<Product> products = (List<Product>) productRepo.findAll();
        model.put("size", products.size());
        model.put("products", products);
        return "main";
    }
}
