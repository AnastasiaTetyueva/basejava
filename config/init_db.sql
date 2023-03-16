create table public.resume
(
    uuid char(36) not null
        constraint resume_pk
            primary key,
    full_name text not null
);

alter table public.resume owner to postgres;

create table public.contact
(
    id serial not null
        constraint contact_pk
            primary key,
    type text not null,
    value text not null,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references public.resume
            on update restrict on delete cascade
);

alter table public.contact owner to postgres;

create unique index contact_uuid_type_index
	on contact (resume_uuid, type);