package de.check24.poc.kotlinpoc.controller

import de.check24.poc.kotlinpoc.model.Book
import de.check24.poc.kotlinpoc.persistence.BookRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.RequestMethod.PUT
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("books")
class BookController(private val bookRepository: BookRepository) {

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): Book? {
        return bookRepository.findById(id).getOrNull()
    }

    @GetMapping("")
    fun findAllBooks(): List<Book>? {
        return bookRepository.findAll()
    }

    @DeleteMapping("{id}")
    fun deleteBook(@PathVariable id: Long): Book? {
        return bookRepository.deleteById(id).getOrNull()
    }

    @RequestMapping(value = [""], method = [POST], consumes = ["application/json"], produces = ["application/json"])
    fun create(@RequestBody book: Book): ResponseEntity<Book?> {
        if (book.id != null || !StringUtils.hasLength(book.title)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
        val maybeBook = bookRepository.save(book)

        if (maybeBook.isEmpty) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(maybeBook.get())
    }

    @RequestMapping(value = [""], method = [PUT], consumes = ["application/json"], produces = ["application/json"])
    fun update(@RequestBody book: Book): ResponseEntity<Book?> {
        if (book.id == null || !StringUtils.hasLength(book.title)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
        val maybeBook = bookRepository.save(book)

        if (maybeBook.isEmpty) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(maybeBook.get())
    }
}