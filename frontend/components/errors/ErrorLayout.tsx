export function ErrorLayout({ children, backgroundColor }: { children: React.ReactNode, backgroundColor?: string; }) {
    return (
        <div className="flex flex-col min-h-screen w-full" style={{ backgroundColor: `${backgroundColor}` }}>
            <main className="flex-grow">
                {children}
            </main>
        </div>
    );
}