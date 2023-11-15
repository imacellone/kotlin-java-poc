package de.check24.poc.kotlinpoc.model

data class Book(var id: Long?, // Read and write
                val title: String // Read-only
)
