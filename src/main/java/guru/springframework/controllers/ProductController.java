package guru.springframework.controllers;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/product")
@Controller
public class ProductController {

    private ProductService productService;  //llamar el servicio no a la implementacion

    //Injecccion automatica de dependencias por setter
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping({"/list", "/"})
    public String listProducts(Model model) {

        System.out.println(productService.listAll().toString());

        //LLamada el servicio para eviar datos a la view
        model.addAttribute("products", productService.listAll());

        return "product/list";
    }

    @RequestMapping("/show/{id}")
    public String getProduct(@PathVariable Integer id, Model model) {

        model.addAttribute("product", productService.getById(id));
        return "product/show";
    }

    //GET REQUEST para nuevo producto
    @RequestMapping("/new")
    public String newProduct(Model model) {

        model.addAttribute("product", new Product());
        return "product/productForm";
    }

    //UPDATE REQUEST
    @RequestMapping("/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "product/productForm";
    }

    //POST REQUEST para formulario (desde new o desde update llegan aqui debido al action en productForm.html)
    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdate(Product product) {
        Product savedProduct = productService.saveOrUpdate(product);

        //Redirecciones automaticamente a URL
        return "redirect:/product/show/" + savedProduct.getId();
    }

    //DELETE REQUEST
    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.delete(id);

        return "redirect:/product/list";
    }


}
