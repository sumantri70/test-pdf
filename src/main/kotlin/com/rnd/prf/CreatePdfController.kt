package com.rnd.prf

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.io.source.ByteArrayOutputStream
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.context.WebContext
import java.io.File
import java.util.*


@RestController
@RequestMapping
class CreatePdfController(
    val htmlTemplateEngine: TemplateEngine
) {

    @GetMapping("")
    fun test(): ResponseEntity<ByteArray> {

        val LANG_CODE_INDONESIA = "in"
        val LOCALE_ID = Locale(LANG_CODE_INDONESIA)
        val LOCALE_DEFAULT = LOCALE_ID
        val locale: Locale = LOCALE_DEFAULT
        val ctx = Context(locale)

        //ctx.setVariable("orderEntry", order)
        val orderHtml: String = htmlTemplateEngine.process("report", ctx)

        val target = ByteArrayOutputStream()

        val converterProperties = ConverterProperties()

        HtmlConverter.convertToPdf(orderHtml, target, converterProperties)
        HtmlConverter.convertToPdf(File("report.html"), File("demo-html.pdf"));

        val bytes: ByteArray = target.toByteArray()

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .body(bytes)
    }

}