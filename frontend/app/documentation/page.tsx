import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import DocumentationContent from '@/components/pages/Documentation';
import React from 'react';

const Documentation = () => {
    return (
        <>
            <HeaderDefault />
            <DocumentationContent />
            <Footer />
        </>
    );
};

export default Documentation;