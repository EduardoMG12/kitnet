import TooManyRequestContent from "@/components/errors/TooManyRequestContent";
import MainLayout from "@/components/layout/MainLayout";
import type { Metadata } from 'next';

export const metadata: Metadata = {
    title: "429 - Muitas Requisições | Kitnet",
    description: "Você fez muitas requisições em um curto período. Por favor, aguarde um momento e tente novamente.",
    // Configurações para evitar indexação por mecanismos de busca
    robots: {
        index: false,
        follow: false,
        nocache: true, // Garante que a página não seja cacheada ou indexada
        googleBot: {
            index: false,
            follow: false,
            noimageindex: true,
            'max-video-preview': -1,
            'max-snippet': -1,
        },
    },
    openGraph: {
        url: 'https://www.kitnet.com.br/errors/too-many-requests', // Ajuste a URL se for diferente
        title: '429 - Muitas Requisições | Kitnet',
        description: 'Você atingiu o limite de requisições. Tente novamente mais tarde.',
        siteName: 'Kitnet',
        type: 'website',
        images: [
            {
                url: 'https://www.kitnet.com.br/images/kitnet-logo-error.png', // Use sua imagem de erro genérica
                width: 800,
                height: 600,
                alt: 'Kitnet - Muitas Requisições',
            },
        ],
    },
    twitter: {
        card: 'summary_large_image',
        title: '429 - Muitas Requisições | Kitnet',
        description: 'Você atingiu o limite de requisições. Tente novamente mais tarde.',
        creator: '@KitnetBR',
        site: '@KitnetBR',
        images: ['https://www.kitnet.com.br/images/kitnet-logo-error.png'],
    },
};



export default function TooManyRequestPage() {
    return (
        <MainLayout hideFooter>
            <TooManyRequestContent />
        </MainLayout>
    );
}
