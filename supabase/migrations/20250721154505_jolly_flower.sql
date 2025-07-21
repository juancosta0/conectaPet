-- Inserir usuários de exemplo
INSERT INTO users (name, email, password, user_type, cnpj, description, created_at, enabled) VALUES
('João Silva', 'joao@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYoHA8t/DKDo.q', 'ADOTANTE', NULL, NULL, NOW(), true),
('Maria Santos', 'maria@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYoHA8t/DKDo.q', 'ADOTANTE', NULL, NULL, NOW(), true),
('ONG Amigos dos Animais', 'ong@amigos.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYoHA8t/DKDo.q', 'ONG', '12.345.678/0001-90', 'ONG dedicada ao resgate e cuidado de animais abandonados', NOW(), true),
('Instituto Pet Feliz', 'contato@petfeliz.org', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYoHA8t/DKDo.q', 'ONG', '98.765.432/0001-10', 'Instituto focado na adoção responsável de pets', NOW(), true);

-- Inserir pets de exemplo
INSERT INTO pets (name, species, breed, age, size, color, description, status, user_id, created_at) VALUES
('Caramelo', 'Cachorro', 'SRD', 2, 'MEDIO', 'Caramelo', 'Amigável e brincalhão. Adora brincar no quintal e é muito carinhoso com crianças. Está castrado e com todas as vacinas em dia.', 'DISPONIVEL', 3, NOW()),
('Mimi', 'Gato', 'Siamês', 3, 'PEQUENO', 'Branco e Cinza', 'Calma e carinhosa. Gosta de ficar no colo e é muito tranquila. Ideal para apartamentos e pessoas que buscam um companheiro sereno.', 'DISPONIVEL', 3, NOW()),
('Rex', 'Cachorro', 'Pastor Alemão', 4, 'GRANDE', 'Preto e Marrom', 'Leal e inteligente. Excelente cão de guarda e muito obediente. Precisa de espaço para correr e se exercitar diariamente.', 'DISPONIVEL', 4, NOW()),
('Luna', 'Gato', 'SRD', 1, 'PEQUENO', 'Frajola', 'Curiosa e cheia de energia. Adora explorar e brincar com bolinhas. Muito sociável com outros gatos e pessoas.', 'DISPONIVEL', 4, NOW()),
('Thor', 'Cachorro', 'Golden Retriever', 5, 'GRANDE', 'Dourado', 'Muito dócil e carinhoso. Ama crianças e é perfeito para famílias. Adora nadar e brincar de buscar a bolinha.', 'DISPONIVEL', 3, NOW()),
('Princesa', 'Gato', 'Persa', 2, 'PEQUENO', 'Branco', 'Elegante e carinhosa. Gosta de cuidados especiais com sua pelagem. Muito tranquila e companheira.', 'DISPONIVEL', 4, NOW());

-- Inserir imagens dos pets
INSERT INTO pet_images (image_url, is_main, pet_id, created_at) VALUES
('https://images.pexels.com/photos/1805164/pexels-photo-1805164.jpeg', true, 1, NOW()),
('https://images.pexels.com/photos/45201/kitty-cat-kitten-pet-45201.jpeg', true, 2, NOW()),
('https://images.pexels.com/photos/333083/pexels-photo-333083.jpeg', true, 3, NOW()),
('https://images.pexels.com/photos/416160/pexels-photo-416160.jpeg', true, 4, NOW()),
('https://images.pexels.com/photos/552598/pexels-photo-552598.jpeg', true, 5, NOW()),
('https://images.pexels.com/photos/774731/pexels-photo-774731.jpeg', true, 6, NOW());