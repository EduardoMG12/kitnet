

import MyPropertiesPage from '@/components/pages/MyPropertiesPage';
import React from 'react';

const mockProperties = [
    {
        id: '1',
        title: 'Casa Moderna no Centro',
        location: 'Centro, São Paulo',
        price: 850000,
        image: '/placeholder.svg',
        type: 'Casa',
        bedrooms: 3,
        bathrooms: 2,
        area: 120,
        status: 'Ativa',
        views: 245,
        proposals: 3
    },
    {
        id: '2',
        title: 'Apartamento Cobertura',
        location: 'Ipanema, Rio de Janeiro',
        price: 1200000,
        image: '/placeholder.svg',
        type: 'Apartamento',
        bedrooms: 4,
        bathrooms: 3,
        area: 180,
        status: 'Pausada',
        views: 156,
        proposals: 1
    },
    {
        id: '3',
        title: 'Casa de Campo',
        location: 'Petrópolis, RJ',
        price: 650000,
        image: '/placeholder.svg',
        type: 'Casa',
        bedrooms: 2,
        bathrooms: 2,
        area: 200,
        status: 'Ativa',
        views: 89,
        proposals: 0
    }
];

const MyProperties = () => {
    return (
        <>

            <MyPropertiesPage properties={mockProperties} />
        </>
    );
};

export default MyProperties;