

import PropertyDetailsPage from '@/components/pages/PropertyDetailsPage';
import React from 'react';

const properties = [
    {
        id: '1',
        title: 'Apartamento moderno com vista para o mar',
        description: 'Este lindo apartamento oferece uma vista deslumbrante para o mar, em uma localização privilegiada em Copacabana. Com acabamento de alto padrão, o imóvel conta com quartos espaçosos, sala ampla e cozinha moderna, ideal para quem busca conforto e qualidade de vida.',
        location: 'Copacabana, Rio de Janeiro',
        price: 2500,
        image: 'https://images.unsplash.com/photo-1483058712412-4245e9b90334?auto=format&fit=crop&w=1200&q=80',
        type: 'Apartamento',
        bedrooms: 2,
        bathrooms: 1,
        area: 75,
        features: [
            { name: 'Garagem para 1 carro', type: 'garage' },
            { name: 'Varanda', type: 'livingroom' },
            { name: 'Cozinha equipada', type: 'kitchen' },
            { name: 'Ar condicionado', type: 'bedroom' },
            { name: 'Piso de madeira', type: 'area' },
            { name: 'Armários embutidos', type: 'bedroom' }
        ],
        nearbyAmenities: [
            { name: 'Praia de Copacabana', distance: '200m' },
            { name: 'Supermercado', distance: '300m' },
            { name: 'Farmácia', distance: '150m' },
            { name: 'Shopping', distance: '1.2km' }
        ],
        additionalImages: [
            'https://images.unsplash.com/photo-1484154218962-a197022b5858?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1502005229762-cf1b2da7c5d6?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1560448204-603b3fc33ddc?auto=format&fit=crop&w=600&h=400&q=80'
        ]
    },
    {
        id: '2',
        title: 'Casa espaçosa com jardim',
        description: 'Linda casa em condomínio fechado no Morumbi, com amplo jardim e área de lazer. A casa possui quartos amplos, suítes modernas e área gourmet completa, perfeita para famílias que buscam conforto e segurança em uma das melhores regiões de São Paulo.',
        location: 'Morumbi, São Paulo',
        price: 4200,
        image: 'https://images.unsplash.com/photo-1487958449943-2429e8be8625?auto=format&fit=crop&w=1200&q=80',
        type: 'Casa',
        bedrooms: 4,
        bathrooms: 3,
        area: 180,
        features: [
            { name: 'Garagem para 2 carros', type: 'garage' },
            { name: 'Jardim privativo', type: 'area' },
            { name: 'Piscina', type: 'area' },
            { name: 'Churrasqueira', type: 'area' },
            { name: 'Suíte master', type: 'bedroom' },
            { name: 'Closet', type: 'bedroom' }
        ],
        nearbyAmenities: [
            { name: 'Parque do Morumbi', distance: '1km' },
            { name: 'Shopping Jardim Sul', distance: '3.5km' },
            { name: 'Escola Internacional', distance: '2km' },
            { name: 'Hospital Albert Einstein', distance: '4km' }
        ],
        additionalImages: [
            'https://images.unsplash.com/photo-1484154218962-a197022b5858?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1502005229762-cf1b2da7c5d6?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1560448204-603b3fc33ddc?auto=format&fit=crop&w=600&h=400&q=80'
        ]
    },
    {
        id: '3',
        title: 'Studio mobiliado no centro',
        description: 'Studio completamente mobiliado e equipado no coração de Florianópolis. Localização estratégica, próximo a restaurantes, comércios e transporte público. Ideal para profissionais que trabalham no centro ou para quem busca praticidade no dia a dia.',
        location: 'Centro, Florianópolis',
        price: 1800,
        image: 'https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=1200&q=80',
        type: 'Studio',
        bedrooms: 1,
        bathrooms: 1,
        area: 45,
        features: [
            { name: 'Mobiliado', type: 'livingroom' },
            { name: 'Cozinha americana', type: 'kitchen' },
            { name: 'Ar condicionado', type: 'bedroom' },
            { name: 'Internet fibra ótica', type: 'area' },
            { name: 'Portaria 24h', type: 'area' },
            { name: 'Academia no prédio', type: 'area' }
        ],
        nearbyAmenities: [
            { name: 'Mercado Público', distance: '500m' },
            { name: 'Terminal de ônibus', distance: '400m' },
            { name: 'Shopping Beiramar', distance: '1km' },
            { name: 'Universidade Federal', distance: '2.5km' }
        ],
        additionalImages: [
            'https://images.unsplash.com/photo-1484154218962-a197022b5858?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1502005229762-cf1b2da7c5d6?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1560448204-603b3fc33ddc?auto=format&fit=crop&w=600&h=400&q=80'
        ]
    },
    {
        id: '4',
        title: 'Apartamento com terraço e churrasqueira',
        description: 'Excelente apartamento em Botafogo com terraço amplo e churrasqueira, perfeito para receber amigos e família. O apartamento conta com três dormitórios, sendo uma suíte, cozinha planejada e ampla sala de estar e jantar.',
        location: 'Botafogo, Rio de Janeiro',
        price: 3200,
        image: 'https://images.unsplash.com/photo-1460925895917-afdab827c52f?auto=format&fit=crop&w=1200&q=80',
        type: 'Apartamento',
        bedrooms: 3,
        bathrooms: 2,
        area: 110,
        features: [
            { name: 'Terraço', type: 'area' },
            { name: 'Churrasqueira', type: 'area' },
            { name: 'Armários planejados', type: 'bedroom' },
            { name: 'Vaga na garagem', type: 'garage' },
            { name: 'Salão de festas', type: 'area' },
            { name: 'Portaria 24h', type: 'area' }
        ],
        nearbyAmenities: [
            { name: 'Metrô Botafogo', distance: '600m' },
            { name: 'Shopping Rio Sul', distance: '1.2km' },
            { name: 'Praia de Botafogo', distance: '800m' },
            { name: 'Parque Lage', distance: '3km' }
        ],
        additionalImages: [
            'https://images.unsplash.com/photo-1484154218962-a197022b5858?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1502005229762-cf1b2da7c5d6?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1560448204-603b3fc33ddc?auto=format&fit=crop&w=600&h=400&q=80'
        ]
    },
    {
        id: '5',
        title: 'Casa de condomínio com piscina',
        description: 'Linda casa em condomínio fechado de alto padrão em Alphaville. Com arquitetura moderna e acabamento premium, a casa possui amplos espaços de convivência, piscina privativa, jardim e área gourmet completa.',
        location: 'Alphaville, São Paulo',
        price: 6500,
        image: 'https://images.unsplash.com/photo-1483058712412-4245e9b90334?auto=format&fit=crop&w=1200&q=80',
        type: 'Casa',
        bedrooms: 4,
        bathrooms: 4,
        area: 250,
        features: [
            { name: 'Piscina privativa', type: 'area' },
            { name: 'Jardim', type: 'area' },
            { name: 'Área gourmet', type: 'area' },
            { name: 'Garagem para 4 carros', type: 'garage' },
            { name: 'Suíte master com closet', type: 'bedroom' },
            { name: 'Sistema de segurança', type: 'area' }
        ],
        nearbyAmenities: [
            { name: 'Shopping Alphaville', distance: '5km' },
            { name: 'Campo de golfe', distance: '2km' },
            { name: 'Escola Internacional', distance: '3km' },
            { name: 'Parque empresarial', distance: '6km' }
        ],
        additionalImages: [
            'https://images.unsplash.com/photo-1484154218962-a197022b5858?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1502005229762-cf1b2da7c5d6?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1560448204-603b3fc33ddc?auto=format&fit=crop&w=600&h=400&q=80'
        ]
    },
    {
        id: '6',
        title: 'Loft industrial reformado',
        description: 'Loft industrial com pé direito alto e estilo urbano na Vila Madalena. O imóvel foi completamente reformado, mantendo o charme industrial com vigas aparentes, tijolos expostos e acabamentos modernos.',
        location: 'Vila Madalena, São Paulo',
        price: 2900,
        image: 'https://images.unsplash.com/photo-1487958449943-2429e8be8625?auto=format&fit=crop&w=1200&q=80',
        type: 'Loft',
        bedrooms: 1,
        bathrooms: 1,
        area: 70,
        features: [
            { name: 'Pé direito duplo', type: 'area' },
            { name: 'Estilo industrial', type: 'area' },
            { name: 'Cozinha integrada', type: 'kitchen' },
            { name: 'Vaga na garagem', type: 'garage' },
            { name: 'Mezanino', type: 'area' },
            { name: 'Varanda', type: 'livingroom' }
        ],
        nearbyAmenities: [
            { name: 'Metrô Vila Madalena', distance: '700m' },
            { name: 'Bares e restaurantes', distance: '200m' },
            { name: 'Parque do Beco', distance: '1km' },
            { name: 'Centro comercial', distance: '500m' }
        ],
        additionalImages: [
            'https://images.unsplash.com/photo-1484154218962-a197022b5858?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1502005229762-cf1b2da7c5d6?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&w=600&h=400&q=80',
            'https://images.unsplash.com/photo-1560448204-603b3fc33ddc?auto=format&fit=crop&w=600&h=400&q=80'
        ]
    }
];

const PropertyDetails = () => {
    return (
        <>

            <PropertyDetailsPage properties={properties} />
        </>
    );
};

export default PropertyDetails;