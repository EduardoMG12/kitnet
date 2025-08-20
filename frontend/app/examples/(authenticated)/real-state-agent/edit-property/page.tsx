// Dados estáticos para demonstração (mesmo do PropertyDetails)
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
    }
];



import EditPropertyPage from '@/components/pages/EditPropertyPage';
import React from 'react';

const EditProperty = () => {
    return (
        <>

            <EditPropertyPage properties={properties} />
        </>
    );
};

export default EditProperty;