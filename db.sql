create table if not exists public.number_searching_result
(
    id        serial primary key,
    number    int  not null,
    code      text not null,
    filenames text[],
    error     text
);

create table if not exists public.numbers_inverted_index
(
    id       serial primary key,
    number   int  not null,
    filename text not null
);
create index inverted_index_numbers on public.numbers_inverted_index (number);
create index inverted_index_filenames on public.numbers_inverted_index (filename);

create table if not exists public.indexed_file
(
    filename    text primary key,
    last_update timestamp with time zone not null
);