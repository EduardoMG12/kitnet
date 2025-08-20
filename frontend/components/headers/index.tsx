

// components/Header.tsx
'use client';

import { useHeaderStore } from '@/store/headerStore';
import AdminHeader from './AdminHeader';
import SupportHeader from './SupportHeader';
import LesseeHeader from './LesseeHeader';
import LessorHeader from './LessorHeader';
import RealStateAgentHeader from './RealStateAgentHeader';
import WithoutLoginHeader from './WithoutLoginHeader';

export default function Header() {
    const currentHeader = useHeaderStore((state) => state.currentHeader);

    const renderHeaderComponent = () => {
        switch (currentHeader) {
            case 'admin':
                return <AdminHeader />;
            case 'support':
                return <SupportHeader />;
            case 'lessee':
                return <LesseeHeader />;
            case 'lessor':
                return <LessorHeader />;
            case 'realStateAgent':
                return <RealStateAgentHeader />;
            case 'withoutLogin':
                return <WithoutLoginHeader />;
            default:
                // Renderiza um header padrão ou o header sem login se não houver um tipo definido
                return <WithoutLoginHeader />;
        }
    };

    return <header>{renderHeaderComponent()}</header>;
}