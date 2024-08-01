create table customer(
    id uuid primary key,
    document_number text not null,
    name text not null,
    type text not null,
    gender text not null,
    date_of_birth date not null,
    email text not null,
    phone_number text not null
);

create table insurance_quote(
    id serial primary key,
    insurance_policy_id integer,
    product_id uuid not null,
    offer_id uuid not null,
    category text not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    total_monthly_premium_amount numeric(10, 2) not null,
    total_coverage_amount numeric(10, 2) not null,
    customer_id uuid not null
);

create table insurance_quote_assistances(
    insurance_quote_id int not null,
    assistances text not null
);

alter table insurance_quote add constraint fk_insurance_quote_customer_id foreign key (customer_id) references customer(id);

create table coverage(
    id uuid primary key,
    insurance_quote_id int not null,
    name text not null,
    value numeric(10, 2) not null
);

alter table coverage add constraint fk_coverage_insurance_id foreign key (insurance_quote_id) references insurance_quote(id);
