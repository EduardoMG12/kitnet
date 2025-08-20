import UnauthorizedContent from "@/components/pages/errors/UnauthorizedContent";
import MainLayout from "@/components/layout/MainLayout";
import type { Metadata } from 'next';

export const metadata: Metadata = {
    title: "401 - Não Autorizado | Kitnet",
    description: "Você não tem as credenciais necessárias para acessar esta página. Por favor, faça login ou verifique suas permissões.",
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
        url: 'https://www.kitnet.com.br/errors/unauthorized', // Ajuste a URL se for diferente
        title: '401 - Não Autorizado | Kitnet',
        description: 'Você não tem as credenciais necessárias para acessar esta página.',
        siteName: 'Kitnet',
        type: 'website',
        images: [
            {
                url: 'https://www.kitnet.com.br/images/kitnet-logo-error.png', // Use uma imagem de erro genérica ou específica
                width: 800,
                height: 600,
                alt: 'Kitnet - Não Autorizado',
            },
        ],
    },
    twitter: {
        card: 'summary_large_image',
        title: '401 - Não Autorizado | Kitnet',
        description: 'Você não tem as credenciais necessárias para acessar esta página.',
        creator: '@KitnetBR',
        site: '@KitnetBR',
        images: ['https://www.kitnet.com.br/images/kitnet-logo-error.png'],
    },
};



export default function UnauthorizedPage() {
    return (
        <UnauthorizedContent />
    );
}