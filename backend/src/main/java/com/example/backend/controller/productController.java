package com.example.backend.controller;

import com.example.backend.common.AuthAccess;
import com.example.backend.common.Result;
import com.example.backend.mapper.productMapper;
import com.example.backend.pojo.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping
public class productController {

    private static final         // 获取项目根路径 + /src/images/
    String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "images";

    @Autowired
    productMapper productMapper;

    @GetMapping("/product/{id}")// 根据产品ID查询产品信息
    public Object getProductById(@PathVariable int id) {
        // 通过产品ID查询产品信息
        System.out.println("getProductById: " + id);
        product product = productMapper.selectById(id);
        // 产品不存在时返回提示
        if (product == null || product.getId() == 0) {
            return "Product not found";
        }
        return product;
    }

    @AuthAccess
    @GetMapping("/products")// 查询所有产品
    public Object getAllProducts() {
        // 查询所有产品
        System.out.println("getAllProducts");
        return productMapper.selectList(null);
    }

    @PostMapping("/product")// 添加新产品
    public Result addProduct(@RequestBody product product) {
        // 添加新产品
        System.out.println("addProduct: " + product);
        int result = productMapper.insert(product);
        if (result > 0) {
            return Result.success("添加成功,商品id："+product.getId());
        } else {
            return Result.error("300", "添加产品失败，请检查输入数据是否正确");
        }
    }

    @PutMapping("/product")
    public Result updateProduct(@RequestBody product product) {
        // 获取原商品信息
        product oldProduct = productMapper.selectById(product.getId());
        if (oldProduct != null && oldProduct.getImage() != null
                && !oldProduct.getImage().equals(product.getImage())) {
            // 如果图片发生变化，删除原图片
            File oldImageFile = new File(filePath + File.separator + oldProduct.getImage());
            if (oldImageFile.exists()) {
                oldImageFile.delete();
            }
        }

        // 更新商品信息
        int result = productMapper.updateById(product);
        if (result > 0) {
            return Result.success("更新成功");
        } else {
            return Result.error("302", "更新产品信息失败，请检查输入数据是否正确");
        }
    }

    @DeleteMapping("/product/{id}")
    public Result deleteProduct(@PathVariable int id) {
        // 先查询商品信息以获取图片文件名
        product product = productMapper.selectById(id);
        if (product != null && product.getImage() != null) {
            // 删除图片文件
            File imageFile = new File(filePath + File.separator + product.getImage());
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }

        // 删除商品记录
        int result = productMapper.deleteById(id);
        if (result > 0) {
            return Result.success("删除成功");
        } else {
            return Result.error("301","删除失败");
        }
    }

    @PostMapping("/uploadImage")
    public Result uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("303", "空文件");
        }
        // 确保目录存在
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 构造保存文件名
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(dir, fileName);

        try {
            file.transferTo(dest);
            System.out.println("上传成功: " + dest.getAbsolutePath());
            return Result.success("上传成功: " + dest.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("500", "上传失败");
        }
    }

    //图片查看接口
    @AuthAccess
    @GetMapping("/productImage/{fileName}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable String fileName) {
        System.out.println("getProductImage: " +filePath + File.separator + fileName);
        File file = new File(filePath + File.separator + fileName);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            HttpHeaders headers = new HttpHeaders();
            // 根据文件扩展名设置内容类型
            String contentType = Files.probeContentType(file.toPath());
            headers.setContentType(MediaType.parseMediaType(contentType));

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
