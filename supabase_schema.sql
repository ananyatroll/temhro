-- =========================================================================
-- SMART STUDY HUB - FULL SUPABASE / POSTGRES SCHEMA
-- Optimized for Offline-First Sync Architectures (WatermelonDB, PowerSync, etc.)
-- Supported packages: Grade 9-12 national exams, Freshman, and AAU UAT prep
-- =========================================================================

-- ----------------------------------------------------
-- 1. Users Table (Maps 1-to-1 with Supabase auth.users)
-- ----------------------------------------------------
create table public.users (
    id uuid references auth.users on delete cascade primary key,
    username text not null default 'Student',
    active_package_id text, -- 'euee_natural', 'euee_social', 'freshman', 'aau_uat'
    score_count integer not null default 0,
    exams_completed integer not null default 0,
    telegram_connected boolean not null default false,
    payment_status text not null default 'none', -- 'none', 'pending', 'approved'
    completed_subjects text default '', -- Comma-separated or serialized completed subject IDs
    
    -- Offline-First Sync Columns
    server_version bigint not null default 1,
    is_deleted boolean not null default false,
    created_at timestamp with time zone default timezone('utc'::text, now()) not null,
    updated_at timestamp with time zone default timezone('utc'::text, now()) not null
);

comment on table public.users is 'Extends auth.users to store students study package tracking & gamification progress.';

-- Enable RLS (Row Level Security)
alter table public.users enable row level security;

create policy "Users can view their own record" on public.users
    for select using (auth.uid() = id);

create policy "Users can update their own record" on public.users
    for update using (auth.uid() = id);


-- ----------------------------------------------------
-- 2. Study Subjects Table
-- ----------------------------------------------------
create table public.study_subjects (
    id text primary key,
    name text not null,
    icon text not null, -- 'biology', 'maths', 'physics', 'chemistry', etc.
    package_id text not null, -- 'euee_natural', 'euee_social', 'freshman', 'aau_uat'
    grade_level integer, -- Reference for secondary school content (9, 10, 11, 12)
    
    -- Offline-First Sync Columns
    server_version bigint not null default 1,
    is_deleted boolean not null default false,
    created_at timestamp with time zone default timezone('utc'::text, now()) not null,
    updated_at timestamp with time zone default timezone('utc'::text, now()) not null
);

alter table public.study_subjects enable row level security;

create policy "Subjects are readable by authenticated users" on public.study_subjects
    for select using (true);


-- ----------------------------------------------------
-- 3. Textbook Notes Table (Instead of standard subject_notes)
-- ----------------------------------------------------
create table public.textbook_notes (
    id uuid default gen_random_uuid() primary key,
    subject_id text references public.study_subjects(id) on delete cascade not null,
    unit text not null, -- 'UNIT 1', 'UNIT 2', 'UNIT 3'
    title text not null,
    content text not null, -- Extensive Markdown-formatted summary text
    
    -- Offline-First Sync Columns
    server_version bigint not null default 1,
    is_deleted boolean not null default false,
    created_at timestamp with time zone default timezone('utc'::text, now()) not null,
    updated_at timestamp with time zone default timezone('utc'::text, now()) not null
);

comment on table public.textbook_notes is 'Contains premium high-yield textbook summaries and syllabus key items.';

alter table public.textbook_notes enable row level security;

create policy "Textbook notes are readable by authenticated users" on public.textbook_notes
    for select using (true);


-- ----------------------------------------------------
-- 4. Exam Questions Table (Ethiopian Curriculum Weighing Optimized)
-- ----------------------------------------------------
create table public.exam_questions (
    id uuid default gen_random_uuid() primary key,
    subject_id text references public.study_subjects(id) on delete cascade not null,
    question_text text not null,
    option_a text not null,
    option_b text not null,
    option_c text not null,
    option_d text not null,
    correct_option text not null check (correct_option in ('A', 'B', 'C', 'D')),
    explanation text not null,
    difficultytext text default 'Medium', -- 'Easy', 'Medium', 'Hard'
    
    -- Offline-First Sync Columns
    server_version bigint not null default 1,
    is_deleted boolean not null default false,
    created_at timestamp with time zone default timezone('utc'::text, now()) not null,
    updated_at timestamp with time zone default timezone('utc'::text, now()) not null
);

comment on table public.exam_questions is 'Unified database of EUEE past papers, Freshman exams, and AAU UAT mock questions.';

alter table public.exam_questions enable row level security;

create policy "Exam questions are readable by authenticated users" on public.exam_questions
    for select using (true);


-- ----------------------------------------------------
-- 5. Flashcards Table (Spaced Repetition Active Recall Cards)
-- ----------------------------------------------------
create table public.flashcards (
    id uuid default gen_random_uuid() primary key,
    subject_id text references public.study_subjects(id) on delete cascade not null,
    front text not null, -- Active recall prompt or keyword
    back text not null, -- Detailed response details
    
    -- Offline-First Sync Columns
    server_version bigint not null default 1,
    is_deleted boolean not null default false,
    created_at timestamp with time zone default timezone('utc'::text, now()) not null,
    updated_at timestamp with time zone default timezone('utc'::text, now()) not null
);

alter table public.flashcards enable row level security;

create policy "Flashcards are readable by authenticated users" on public.flashcards
    for select using (true);


-- ----------------------------------------------------
-- 6. Payment Verifications Table (Supervisor Manual Review Queue)
-- ----------------------------------------------------
create table public.payment_verifications (
    id uuid default gen_random_uuid() primary key,
    user_id uuid references public.users(id) on delete cascade not null,
    txn_id text unique not null,
    sender_phone text not null,
    screenshot_url text, -- Live bucket URL containing the proof receipt
    status text not null default 'pending' check (status in ('pending', 'approved', 'rejected')),
    
    -- Offline-First Sync Columns
    server_version bigint not null default 1,
    is_deleted boolean not null default false,
    submitted_at timestamp with time zone default timezone('utc'::text, now()) not null,
    processed_at timestamp with time zone
);

comment on table public.payment_verifications is 'Queue storing manual credit bank transfers for verification by administrative supervisors.';

alter table public.payment_verifications enable row level security;

create policy "Users can view their own payment listings" on public.payment_verifications
    for select using (auth.uid() = user_id);

create policy "Users can initiate payment verification requests" on public.payment_verifications
    for insert with check (auth.uid() = user_id);


-- -------------------------------------------------------------------------
-- INDEXES FOR INSTANT INCREMENTAL SYNC QUERIES
-- Redundant B-Tree index structures targeting server_version and updated_at
-- -------------------------------------------------------------------------
create index idx_users_sync_version on public.users (server_version, updated_at);
create index idx_subjects_sync_version on public.study_subjects (server_version, updated_at);
create index idx_textbook_notes_sync_version on public.textbook_notes (server_version, updated_at);
create index idx_questions_sync_version on public.exam_questions (server_version, updated_at);
create index idx_flashcards_sync_version on public.flashcards (server_version, updated_at);
create index idx_payments_sync_version on public.payment_verifications (server_version);


-- -------------------------------------------------------------------------
-- AUTOMATED TRIGGERS FOR SYNC VERSION INCREMENTS AND UPDATED AT STAMPS
-- -------------------------------------------------------------------------

-- Crucial: Automatically updates updated_at whenever a column value changes
create or replace function public.trigger_set_updated_at()
returns trigger as $$
begin
    new.updated_at = now();
    return new;
end;
$$ language plpgsql;

create trigger t_set_updated_at_users
    before update on public.users
    for each row execute procedure public.trigger_set_updated_at();

create trigger t_set_updated_at_subjects
    before update on public.study_subjects
    for each row execute procedure public.trigger_set_updated_at();

create trigger t_set_updated_at_notes
    before update on public.textbook_notes
    for each row execute procedure public.trigger_set_updated_at();

create trigger t_set_updated_at_questions
    before update on public.exam_questions
    for each row execute procedure public.trigger_set_updated_at();

create trigger t_set_updated_at_flashcards
    before update on public.flashcards
    for each row execute procedure public.trigger_set_updated_at();


-- Increment Master Server Version function for robust conflict-free synchronization
create or replace function public.bump_server_version()
returns trigger as $$
begin
    new.server_version = nextval('public.server_version_seq');
    return new;
end;
$$ language plpgsql;

-- Sequence needed to serve incrementing global sync versions
create sequence public.server_version_seq start with 1;

create trigger t_bump_version_users
    before insert or update on public.users
    for each row execute procedure public.bump_server_version();

create trigger t_bump_version_subjects
    before insert or update on public.study_subjects
    for each row execute procedure public.bump_server_version();

create trigger t_bump_version_notes
    before insert or update on public.textbook_notes
    for each row execute procedure public.bump_server_version();

create trigger t_bump_version_questions
    before insert or update on public.exam_questions
    for each row execute procedure public.bump_server_version();

create trigger t_bump_version_flashcards
    before insert or update on public.flashcards
    for each row execute procedure public.bump_server_version();

create trigger t_bump_version_payments
    before insert or update on public.payment_verifications
    for each row execute procedure public.bump_server_version();
