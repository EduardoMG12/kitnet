import BadRequestContent from "@/components/pages/errors/BadRequestContent";
import MainLayout from "@/components/layout/MainLayout";
import type { Metadata } from 'next';

export const metadata: Metadata = {
    title: "400 - Requisição Inválida | Kitnet",
    description: "Sua requisição não pôde ser processada. Verifique os dados enviados ou tente novamente mais tarde. Se o problema persistir, entre em contato com o suporte.",
    // Configurações para evitar indexação por mecanismos de busca
    robots: {
        index: false,
        follow: false,
        nocache: true,
        googleBot: {
            index: false,
            follow: false,
            noimageindex: true,
            'max-video-preview': -1,
            'max-snippet': -1,
        },
    },
    openGraph: {
        url: 'https://www.kitnet.com.br/errors/bad-request', // Ajuste a URL se for diferente
        title: '400 - Requisição Inválida | Kitnet',
        description: 'Sua requisição não pôde ser processada. Verifique os dados enviados.',
        siteName: 'Kitnet',
        type: 'website',
        images: [
            {
                url: 'https://www.kitnet.com.br/images/kitnet-logo-error.png', // Use uma imagem de erro genérica ou específica
                width: 800,
                height: 600,
                alt: 'Kitnet - Requisição Inválida',
            },
        ],
    },
    twitter: {
        card: 'summary_large_image',
        title: '400 - Requisição Inválida | Kitnet',
        description: 'Sua requisição não pôde ser processada.',
        creator: '@KitnetBR',
        site: '@KitnetBR',
        images: ['https://www.kitnet.com.br/images/kitnet-logo-error.png'],
    },
};



export default function BadRequestPage() {
    return (

        <BadRequestContent />

    );
}