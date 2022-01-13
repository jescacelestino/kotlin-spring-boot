package com.mercadolivro.service

import com.mercadolivro.enums.BookStatusEnum
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun findAll(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAll(pageable)
    }

    fun findByStatusActive(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAllByStatus(BookStatusEnum.ACTIVE, pageable)
    }

    fun findById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow()
    }

    fun deleteById(id: Int) {
        val book = findById(id)
        book.status = BookStatusEnum.CANCELED
        update(book)
    }

    fun update(book: BookModel) {
        bookRepository.save(book)
    }

    fun deleteByCustumer(customer: CustomerModel) {
        val books =  bookRepository.findByCustomer(customer)
        for (book in books) {
            book.status = BookStatusEnum.DELETED
        }
        bookRepository.saveAll(books)
    }

}
