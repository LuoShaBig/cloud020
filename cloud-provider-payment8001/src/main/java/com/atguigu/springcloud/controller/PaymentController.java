package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.dto.CommonResult;
import com.atguigu.springcloud.entity.Payment;
import com.atguigu.springcloud.util.PDFKit;
import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/payment/one")
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    @Resource
    private PDFKit pdfKit;
    @Value("${server.port}")
    private String serverPort;

    //只传给前端CommonResult，不需要前端了解其他的组件
    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        Integer result = paymentService.create(payment);
        log.info("*****插入结果："+result);
        if(result > 0){
            return new CommonResult(200,"插入数据成功,serverPost:"+serverPort,result);
        }else{
            return new CommonResult(444,"插入数据失败",null);
        }
    }
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") String id, HttpServletResponse response) throws Throwable {
        List<Payment> payment = paymentService.getPaymentById(id);
        pdfKit.downLoad(response,"F:\\用友上汽\\考勤模板.docx");
        log.info("*****插入结果："+payment);
        if(!CollectionUtils.isEmpty(payment)){
            return new CommonResult(200,"查询成功,serverPost:"+serverPort,payment);
        }else{
            return new CommonResult(444,"没有对应记录,查询ID："+id,null);
        }
    }

    public void reportExportPDF(){
        String html = PDFKit.readFileByUrl("E:\\qq文件\\上课视频和资料\\html\\day1\\js\\ReportedList.html"); // 将html代码读取到html字符串中

        try {
            Document document = new Document();
            PdfWriter mPdfWriter = PdfWriter.getInstance(document, new FileOutputStream(new File("F:\\用友上汽\\滴滴电子发票.pdf")));
            document.open();
            ByteArrayInputStream bin = new ByteArrayInputStream(html.getBytes());
            XMLWorkerHelper.getInstance().parseXHtml(mPdfWriter, document, bin, null, new ChinaFontProvide());
            System.out.println("生成完毕");
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static final class ChinaFontProvide implements FontProvider {
        @Override
        public boolean isRegistered(String s) {
            return false;
        }
        @Override
        public Font getFont(String arg0, String arg1, boolean arg2, float arg3, int arg4, BaseColor arg5) {
            BaseFont bfComic = null;
            try {
                bfComic = BaseFont.createFont("C:\\Windows\\Fonts\\SimHei Regular", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return new Font(bfComic, 12, Font.NORMAL);
        }
    }


}
